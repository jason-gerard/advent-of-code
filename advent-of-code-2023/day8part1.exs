{:ok, content} = File.read("inputs/day8.txt")

[directions, rest] = content |> String.split("\n", trim: true, parts: 2)

directions = directions |> String.split("", trim: true) |> Enum.map(fn direction -> case direction do
  "R" -> 1
  "L" -> 0
end end)
IO.inspect directions

nodes = rest |> String.split("\n", trim: true) |> Enum.map(fn line -> 
  [start, rest] = line |> String.replace(" ", "") |> String.split("=")
  [left, right] = rest |> String.split(",")
  [left] = left |> String.split("(", trim: true)
  [right] = right |> String.split(")", trim: true)

  {start, [left, right]}
end)
|> Enum.into(%{})
IO.inspect(nodes, charlists: :as_lists)

defmodule Solution do
  def find_path(directions, nodes, curr_node, steps) do
    if curr_node != "ZZZ" do
      direction = directions |> Enum.at(rem(steps, length(directions)))
      next_node = Map.get(nodes, curr_node) |> Enum.at(direction)
      find_path(directions, nodes, next_node, steps+1)
    else
      steps
    end
  end
end

solution = Solution.find_path(directions, nodes, "AAA", 0)
IO.inspect solution