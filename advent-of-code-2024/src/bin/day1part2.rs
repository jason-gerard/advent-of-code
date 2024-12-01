use std::fs;

fn main() {
    let file_path = "src/bin/inputs/day1part2";
    let contents = fs::read_to_string(file_path)
        .expect("Should have been able to read the file");

    let location_ids = contents
        .trim()
        .lines()
        .map(|line| line
            .split("   ")
            .map(|item| item.parse::<i64>().unwrap())
            .collect::<Vec<_>>())
        .collect::<Vec<_>>();

    let mut left_ids = vec![];
    let mut right_ids = vec![];
    for pair in location_ids.iter() {
        left_ids.push(pair[0]);
        right_ids.push(pair[1]);
    }

    let similarity_score = left_ids.iter()
        .map(|l_id| (*l_id as usize) * right_ids
            .iter()
            .filter(|r_id| *r_id == l_id)
            .count())
        .sum::<usize>();

    println!("The similarity score is {similarity_score}");
}