use rusted_cypher::GraphClient;
use rusted_cypher::Statement;
use rusted_cypher::cypher::CypherQuery;
use rusted_cypher::error::GraphError;

use indoc::indoc;

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
    zadanie10(&mut graph)?;

    Ok(())
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
