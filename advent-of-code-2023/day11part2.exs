{:ok, content} = File.read("inputs/day11.txt")

points = content |> String.split("\n", trim: true) |> Enum.map(&String.split(&1, "", trim: true))
IO.inspect(points, charlists: :as_lists)

expanded_rows = points |> Enum.with_index |> Enum.flat_map(fn {row, i} -> if Enum.all?(row, &(&1 == ".")), do: [i], else: [] end)
IO.inspect(expanded_rows, charlists: :as_lists)

expanded_cols = points
|> Enum.zip_with(&Function.identity/1)
|> Enum.with_index |> Enum.flat_map(fn {row, j} -> if Enum.all?(row, &(&1 == ".")), do: [j], else: [] end)
IO.inspect(expanded_cols, charlists: :as_lists)

galaxies = points
|> Enum.with_index
|> Enum.flat_map(fn {row, i} ->
  row |> Enum.with_index |> Enum.flat_map(fn {point, j} -> if point == "#", do: [{i, j}], else: [] end)
end)
pairs = galaxies |> Enum.flat_map(fn p1 -> galaxies |> Enum.flat_map(fn p2 -> if p1 != p2 && p1 > p2, do: [{p1, p2}], else: [] end) end)
IO.inspect(pairs, charlists: :as_lists)

# Since we are working on a manhattan grid we can just take the abs value between components
expand = 1000000
distances = pairs
|> Enum.map(fn {{i1, j1}, {i2, j2}} ->
  base_dist = abs(i1 - i2) + abs(j1 - j2)
  num_rows = expanded_rows |> Enum.filter(&(&1 > min(i1, i2) && &1 < max(i1, i2))) |> length
  num_cols = expanded_cols |> Enum.filter(&(&1 > min(j1, j2) && &1 < max(j1, j2))) |> length
  base_dist - num_rows - num_cols + (expand * num_rows) + (expand * num_cols)
end)
|> Enum.sum

IO.inspect(distances, charlists: :as_lists)
