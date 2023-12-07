{:ok, content} = File.read("inputs/day6.txt")
lines = content |> String.split("\n", trim: true)

[times, distances] = lines |> Enum.map(fn line ->
line |> String.split(":", trim: true) |> Enum.at(1) |> String.split(" ", trim: true) |> Enum.map(fn value -> Integer.parse(value) |> elem(0) end)
end)
pairs = Enum.zip(times, distances)
IO.inspect pairs

winning_strategies = pairs |> Enum.map(fn {duration, winning_distance} ->
  strategies = 0..duration |> Enum.map(fn btn_duration -> {duration - btn_duration, btn_duration} end)
  strategies
  |> Enum.map(fn {travel_duration, speed} -> travel_duration * speed end)
  |> Enum.filter(fn distance -> distance > winning_distance end)
end)

IO.inspect(winning_strategies, charlists: :as_lists)

result = winning_strategies |> Enum.reduce(1, fn strategies, acc -> length(strategies) * acc end)
IO.inspect result