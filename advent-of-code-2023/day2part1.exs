{:ok, content} = File.read("inputs/day2part1.txt")
lines = content |> String.split("\n", trim: true)

games = lines |> Enum.map(fn line -> 
  parts = String.split(line, " ", parts: 3, trim: true)
  IO.inspect parts
  
  game_id = parts
    |> Enum.at(1)
    |> (fn part -> String.slice(part, 0..String.length(part)-2) end).()
    |> Integer.parse
    |> elem(0)
  
  runs = parts
    |> Enum.at(2)
    |> String.split(";", trim: true)
    |> Enum.map(fn run ->
        cube_pairs = run
          |> String.split(",", trim: true)
          |> Enum.reduce(%{}, fn grab, acc ->
              grab_parts = String.split(grab, " ", trim: true)
              Map.put(acc, Enum.at(grab_parts, 1), Integer.parse(Enum.at(grab_parts, 0)) |> elem(0))
             end)
        
       end)
  
  %{
    :id => game_id,
    :runs => runs
  }
end)

IO.inspect games

colors = %{
  "red" => 12,
  "green" => 13,
  "blue" => 14,
}

possible_games = games |> Enum.filter(fn game ->
  game
    |> Map.fetch!(:runs)
    |> Enum.all?(fn run ->
       run |> Enum.all?(fn {k, v} -> Map.fetch!(colors, k) >= v end)
       end)
end)

result = possible_games
  |> Enum.map(fn game -> Map.fetch!(game, :id) end)
  |> Enum.sum


IO.inspect result