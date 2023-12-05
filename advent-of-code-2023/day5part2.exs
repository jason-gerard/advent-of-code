{:ok, content} = File.read("inputs/day5.txt")

[seed_line, rest] = content |> String.split("\n", parts: 2, trim: true)

seed_ranges = seed_line 
|> String.split(":", trim: true)
|> Enum.at(1)
|> String.split(" ", trim: true)
|> Enum.map(&Integer.parse(&1) |> elem(0))
|> Enum.chunk_every(2, 2)

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
maps |> IO.inspect(charlists: :as_lists)

seed = Enum.to_list(0..300000000)
|> Enum.reduce_while(0, fn location, seed ->
  possible_seed = Enum.reduce(maps, location, fn map, acc ->
#    acc |> IO.inspect(charlists: :as_lists)
    match = map |> Enum.find(fn [source, _d, range] -> acc >= source and acc < source + range end)
    case match do
      [source, dest, _r] -> acc + dest - source
      _ -> acc
    end
  end)
  is_in_range = seed_ranges |> Enum.any?(fn [start, range] -> possible_seed >= start && possible_seed < start + range end)
  if is_in_range do
    {:halt, [possible_seed, location]}
  else
    {:cont, seed}
  end
end)

seed |> IO.inspect(charlists: :as_lists)

