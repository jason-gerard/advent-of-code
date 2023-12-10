{:ok, content} = File.read("inputs/day10.txt")

points = content
|> String.split("\n", trim: true)
|> Enum.map(fn line -> String.split(line, "", trim: true) end)
IO.inspect(points, charlists: :as_lists)

{i, j} = points
|> Enum.with_index |> Enum.flat_map(fn {row, i} ->
  row |> Enum.with_index |> Enum.map(fn {point, j} -> {point, i, j} end)
end)
|> Enum.find_value(fn {point, i, j} -> if point == "S", do: {i, j} end)

defmodule Solution do
  def find_path(points, {i, j}, prev_direction, path) do
    point = points |> Enum.at(i) |> Enum.at(j) 
    if point == "S" && length(path) > 0 do
      path |> Enum.reverse
    else
      [next_index, next_direction] = case [point, prev_direction] do
        ["S", nil] -> [] # Starting point
        ["|", :north] -> [{i-1, j}, :north]
        ["|", :south] -> [{i+1, j}, :south]
        ["-", :east] -> [{i, j+1}, :east]
        ["-", :west] -> [{i, j-1}, :west]
        ["L", :south] -> [{i, j+1}, :east]
        ["L", :west] -> [{i-1, j}, :north]
        ["J", :south] -> [{i, j-1}, :west]
        ["J", :east] -> [{i-1, j}, :north]
        ["F", :north] -> [{i, j+1}, :east]
        ["F", :west] -> [{i+1, j}, :south]
        ["7", :north] -> [{i, j-1}, :west]
        ["7", :east] -> [{i+1, j}, :south]
      end
      find_path(points, next_index, next_direction, [{i, j} | path])
    end
  end
end

starting_dirs = [:north, :south, :east, :west] |> Enum.filter(fn direction ->
  case direction do
    :north ->
      point = points |> Enum.at(i-1) |> Enum.at(j)
      point == "F" || point == "7" || point == "|"
    :south ->
      point = points |> Enum.at(i+1) |> Enum.at(j)
      point == "J" || point == "L" || point == "|"
    :east ->
      point = points |> Enum.at(i) |> Enum.at(j+1)
      point == "J" || point == "7" || point == "-"
    :west ->
      point = points |> Enum.at(i) |> Enum.at(j-1)
      point == "L" || point == "F" || point == "-"
  end
end)

paths = starting_dirs |> Enum.map(fn dir -> case dir do
  :north -> Solution.find_path(points, {i-1, j}, dir, [{i, j}])
  :south -> Solution.find_path(points, {i+1, j}, dir, [{i, j}])
  :east -> Solution.find_path(points, {i, j+1}, dir, [{i, j}])
  :west -> Solution.find_path(points, {i, j-1}, dir, [{i, j}])
end end)
IO.inspect(paths, charlists: :as_lists)

IO.inspect((paths |> Enum.at(0) |> length) / 2, charlists: :as_lists)
