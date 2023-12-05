{:ok, content} = File.read("inputs/day5.txt")

[seed_line, rest] = content |> String.split("\n", parts: 2, trim: true)

seeds = seed_line 
|> String.split(":", trim: true)
|> Enum.at(1)
|> String.split(" ", trim: true)
|> Enum.map(&Integer.parse(&1) |> elem(0))
IO.inspect seeds

maps = rest
|> String.split("\n", trim: true)
|> Enum.reduce([], fn part, acc ->
  if String.contains?(part, ":") do
    [[] | acc]
  else
    range = part |> String.split(" ", trim: true) |> Enum.map(&Integer.parse(&1) |> elem(0))
#    source_pairs = source..(source+range-1)
#    dest_pairs = dest..(dest+range-1)
#    map = Enum.zip(source_pairs, dest_pairs) |> Enum.into(%{})
    
    [[range | hd(acc)] | tl(acc)]
  end
end)
|> Enum.reverse
maps |> IO.inspect(charlists: :as_lists)

location = seeds
|> Enum.map(fn seed ->
  Enum.reduce(maps, seed, fn map, acc ->
#    acc |> IO.inspect(charlists: :as_lists)
    match = map |> Enum.find(fn [dest, source, range] -> acc >= source and acc < source + range end)
    case match do
      [dest, source, range] -> acc + dest - source
      _ -> acc
    end
  end)
end)
|> Enum.min

location |> IO.inspect(charlists: :as_lists)

