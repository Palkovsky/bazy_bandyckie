use rusted_cypher::GraphClient;
use rusted_cypher::error::GraphError;

type Res<T> = Result<T, GraphError>;

const CONN_URL: &'static str = "http://neo4j:neo4j@localhost:7474/db/data";

fn main() -> Res<()> {
    let mut graph = GraphClient::connect(CONN_URL)?;

    zadanie3(&mut graph)?;

    Ok(())
}

fn zadanie3(graph: &mut GraphClient) -> Res<()> {
    let result = graph.exec(
        "MATCH (n:Movie) WHERE n.released > 2000 RETURN n.title, n.tagline, n.released LIMIT 25")?;

    for row in result.rows() {
        let (title, tagline, release) =
            (row.get::<String>("n.title")?,
             row.get::<String>("n.tagline")?,
             row.get::<usize>("n.released")?);

        println!("{} | {}\r\n{}\r\n--------", title, release, tagline);
    }

    Ok(())
}
