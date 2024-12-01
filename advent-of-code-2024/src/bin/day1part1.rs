use std::fs;

fn main() {
    let file_path = "src/bin/inputs/day1part1";
    let contents = fs::read_to_string(file_path)
        .expect("Should have been able to read the file");

    let location_ids = contents
        .trim()
        .lines()
        .map(|line| line
            .split("   ")
            .map(|item| item.parse::<i32>().unwrap())
            .collect::<Vec<_>>())
        .collect::<Vec<_>>();

    let mut left_ids = vec![];
    let mut right_ids = vec![];
    for pair in location_ids.iter() {
        left_ids.push(pair[0]);
        right_ids.push(pair[1]);
    }

    left_ids.sort();
    right_ids.sort();

    let total = left_ids
        .into_iter()
        .zip(right_ids.into_iter())
        .map(|(l, r)| (l - r).abs())
        .sum::<i32>();

    println!("Total differences is {total}");
}