use rusted_cypher::GraphClient;
use rusted_cypher::Statement;
use rusted_cypher::cypher::CypherQuery;
use rusted_cypher::error::GraphError;

use indoc::indoc;

use std::collections::{ HashSet, HashMap };

type Res<T> = Result<T, GraphError>;

const CONN_URL: &'static str = "http://neo4j:neo4j@localhost:7474/db/data";

fn main() -> Res<()> {
    let mut graph = GraphClient::connect(CONN_URL)?;

    // zadanie3(&mut graph)?;
    // zadanie4(&mut graph)?;
    // zadanie5(&mut graph)?;
    // zadanie6(&mut graph)?;
    // zadanie7a(&mut graph)?;
    // zadanie7b(&mut graph)?;
    // zadanie9(&mut graph)?;
    // zadanie10(&mut graph)?;
    zadanie13(&mut graph)?;

    Ok(())
}

fn zadanie13(graph: &mut GraphClient) -> Res<(HashSet<usize>, HashSet<(usize, usize)>)> {
    let result = graph.exec("MATCH (a)-[r]-(b) RETURN ID(a) AS id_a, ID(b) AS id_b")?;

    // HashSet containing all graph edges.
    let mut edges: HashSet<(usize, usize)> = result
        .rows()
        .map(|row| (row.get("id_a").unwrap(), row.get("id_b").unwrap()))
        .fold(HashSet::new(), |mut acc, x| {
            // Assume everyting is bidirectional
            acc.insert(x);
            acc.insert((x.1, x.0));
            acc
        });

    // Map of (id: bool). Boolean value tells whether the node was visited or not.
    let mut vertexes = edges
        .clone()
        .into_iter()
        .fold(HashMap::<usize, bool>::new(), |mut acc, x| {
            acc.insert(x.0, false);
            acc.insert(x.1, false);
            acc
        });

    // Count nodes
    let node_count = vertexes.keys().len();

    // Generate tree
    let (nodes, edges) = dfs_st(*vertexes.keys().next().unwrap(),
                                None,
                                &mut vertexes,
                                &mut edges);
    println!("NODES");
    println!("{:?}", nodes);
    println!("EDGES");
    println!("{:?}", edges);

    // Check if DFS had traversed all nodes.
    // If didn't it means that graph is disconnected, in this case return error.
    if nodes.len() != node_count {
        return Err(GraphError::from("Graph disconnected.".to_string()));
    }

    edges.iter().for_each(|(n, m)| {
        let q = Statement::new(
            "MATCH (n), (m) WHERE ID(n)={id1} AND ID(m)={id2} RETURN n.name, m.name"
            )
            .with_param("id1", n).unwrap()
            .with_param("id2", m).unwrap();

        for row in graph.exec(q).unwrap().rows() {
            let (name1, name2) =
                (row.get::<String>("n.name").unwrap(),
                 row.get::<String>("m.name").unwrap());

            println!("----------\r\n{} | {}", name1, name2);
        }
    });

    // Return nodes and edges of spanning tree.
    Ok((nodes, edges))
}

fn dfs_st(node: usize,
          from: Option<usize>,
          vertexes: &mut HashMap<usize, bool>,
          edges: &mut HashSet<(usize, usize)>) -> (HashSet<usize>, HashSet<(usize, usize)>) {

    let visited = *vertexes.get(&node).unwrap();

    // When node already visited return neutral elements.
    if visited {
        return (HashSet::new(), HashSet::new())
    }

    // Generate neighbours set.
    let neighbours: HashSet<usize> = edges
        .iter()
        .filter(|&(key, value)| *key == node || *value == node)
        .map(|(key, value)| {
            if node == *key { *value }
            else { *key }
        })
        // Filter out visited neighbours
        .filter(|neighbour| !*vertexes.get(&neighbour).unwrap())
         // Create set of neighbour nodes.
        .collect();

    // Mark current node as visited.
    vertexes.insert(node, true);

    let mut f_nodes = HashSet::new();
    let mut f_edges = HashSet::new();

    f_nodes.insert(node);
    if let Some(from) = from {
        f_edges.insert((from, node));
        f_edges.insert((node, from));
    }

    // Run dfs_st on neighbours and join results.
    neighbours
        .iter()
        .map(|neighbour| dfs_st(*neighbour, Some(node), vertexes, edges))
        .for_each(|(g_nodes, g_edges)| {
            f_nodes.extend(g_nodes);
            f_edges.extend(g_edges);
        });

    return (f_nodes, f_edges);
}

fn zadanie10(graph: &mut GraphClient) -> Res<()> {
    let result = graph.exec(
        "MATCH p=(a:Person {name: 'Kevin Bacon'})-
         [r1]-(n)-[*2]-(m)-[r3]-
         (b:Person {name: 'Keanu Reeves'})
         RETURN n, m")?;

    // API nie ułatwia wyłuskiwania danych z takich obiektów, więc
    // użyłem debug printa.
    result.data
        .iter()
        .next()
        .map(|row| println!("{:?}", row));

    Ok(())
}

fn zadanie9(graph: &mut GraphClient) -> Res<()> {
    graph.exec(
        "MATCH p=shortestPath((a:Person {name: 'Kevin Bacon'})-[*]-(b:Person {name: 'Keanu Reeves'}))
         WITH NODES(p) AS nds
         UNWIND nds AS ns
         SET ns.mark=true
         RETURN ns"
    )?;
    Ok(())
}

fn zadanie7b(graph: &mut GraphClient) -> Res<()> {
    let result = graph.exec(
        "MATCH (n:Person) -[:ACTED_IN]-> (m:Movie)
         WITH n, LENGTH(COLLECT(m)) as cnt
         WHERE cnt >= 3
         RETURN AVG(cnt) as avg"
    )?;

    for row in result.rows() {
        let avg = row.get::<f64>("avg")?;
        println!("{}", avg);
    }

    Ok(())
}

fn zadanie7a(graph: &mut GraphClient) -> Res<()> {
    let result = graph.exec(
        "MATCH (n:Person) -[:ACTED_IN]-> (m:Movie)
         WITH n, LENGTH(COLLECT(m)) as cnt
         WHERE cnt >= 2
         RETURN n.name, cnt"
    )?;

    for row in result.rows() {
        let (name, cnt) =
            (row.get::<String>("n.name")?,
             row.get::<usize>("cnt")?);

        println!("{} | {} \r\n--------", name, cnt);
    }

    Ok(())
}

fn zadanie6(graph: &mut GraphClient) -> Res<()> {
    graph.exec(
        "MATCH (n: Movie) WHERE n.released > 2000
        SET n.title = n.title + ' ' + n.released
        RETURN n"
    )?;
    Ok(())
}

fn zadanie5(graph: &mut GraphClient) -> Res<()> {
    let mk_props = |act_name: &str, birthplace: &str, salary: usize| -> Res<Statement> {
        Statement::new(indoc!(
            "MATCH (n: Person { name: {name} })
            SET n.birthplace = {birthplace}, n.salary = {salary}"
        ))
            .with_param("name", act_name)?
            .with_param("birthplace", birthplace)?
            .with_param("salary", salary)
            .or(Err(GraphError::from("Invalid statement".to_string())))
    };

    let q = mk_props("Lech Łotocki", "Kraków", 1000000)?;
    graph.exec(q)?;

    Ok(())
}

fn zadanie3(graph: &mut GraphClient) -> Res<()> {
    let result = graph.exec(
        "MATCH (n:Movie) WHERE n.released > 2000 RETURN n.title, n.tagline, n.released LIMIT 25"
    )?;

    for row in result.rows() {
        let (title, tagline, release) =
            (row.get::<String>("n.title")?,
             row.get::<String>("n.tagline")?,
             row.get::<usize>("n.released")?);

        println!("{} | {}\r\n{}\r\n--------", title, release, tagline);
    }

    Ok(())
}

fn zadanie4(graph: &mut GraphClient) -> Res<()> {
    let mk_movie = |q: &mut CypherQuery, title: &str, tagline: &str, released: usize| -> Res<()> {
        let statement = Statement::new(
            "CREATE (n: Movie { title: {title}, tagline: {tagline}, released: {released} })"
        )
            .with_param("title", title)?
            .with_param("tagline", tagline)?
            .with_param("released", released)?;

        q.add_statement(statement);

        Ok(())
    };

    let mk_actor = |q: &mut CypherQuery, name: &str, born: usize| -> Res<()> {
        let statement = Statement::new(
            "CREATE (n: Person { name: {name}, born: {born} })"
        )
            .with_param("name", name)?
            .with_param("born", born)?;

        q.add_statement(statement);

        Ok(())
    };

    let mk_acted_in = |q: &mut CypherQuery, mov_title: &str, act_name: &str| -> Res<()> {
        // indoc to makro, które usuwa wcięcia przy wielolinioych stringach
        let statement = Statement::new(indoc!(
            "MATCH (n: Movie { title: {title} }), (m: Person { name: {name} })
             CREATE (m)-[:ACTED_IN]->(n)"
        ))
            .with_param("title", mov_title)?
            .with_param("name", act_name)?;

        q.add_statement(statement);

        Ok(())
    };

    let mut query = graph.query();
    mk_movie(&mut query, "Smoleńsk", "Prawda zwycięży", 2016)?;

    mk_actor(&mut query, "Lech Łotocki", 1947)?;
    mk_acted_in(&mut query, "Smoleńsk", "Lech Łotocki")?;

    mk_actor(&mut query, "Aldona Struzik", 1964)?;
    mk_acted_in(&mut query, "Smoleńsk", "Aldona Struzik")?;

    mk_actor(&mut query, "Beata Fido", 1967)?;
    mk_acted_in(&mut query, "Smoleńsk", "Beata Fido")?;

    query.send().map(|_| ())
}
