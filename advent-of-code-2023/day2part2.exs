{:ok, content} = File.read("inputs/day2part2.txt")
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

max_values = games |> Enum.map(fn game ->
  game
    |> Map.fetch!(:runs)
    |> Enum.reduce(%{}, fn run, acc ->
        Map.merge(acc, run, fn _k, v1, v2 -> max(v1, v2) end)
       end)
end)

result = max_values 
  |> Enum.map(fn run ->
      Enum.reduce(run, 1, fn {_k, v}, acc -> acc * v end)
     end)
  |> Enum.sum


IO.inspect result