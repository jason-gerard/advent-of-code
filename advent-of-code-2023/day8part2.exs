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
    if !String.ends_with?(curr_node, "Z") do
      direction = directions |> Enum.at(rem(steps, length(directions)))
      next_node = Map.get(nodes, curr_node) |> Enum.at(direction)
      find_path(directions, nodes, next_node, steps+1)
    else
      steps
    end
  end
  
#  def find_path(directions, nodes, curr_node, steps) do
#    direction = directions |> Enum.at(rem(steps, length(directions)))
#    Map.get(nodes, curr_node) |> Enum.at(direction)
#  end
#  
#  def find_multi_path(directions, nodes, curr_nodes, steps) do
#    is_all_final = curr_nodes |> Enum.all?(fn node -> String.ends_with?(node, "Z") end)
#    if is_all_final do
#      steps
#    else
#      next_nodes = curr_nodes |> Enum.map(fn node -> find_path(directions, nodes, node, steps) end)
#      find_multi_path(directions, nodes, next_nodes, steps+1)
#    end
#  end
end

starting_nodes = nodes |> Map.keys() |> Enum.filter(fn node -> String.ends_with?(node, "A") end)
IO.inspect starting_nodes

steps = starting_nodes
|> Enum.map(&Solution.find_path(directions, nodes, &1, 0))
|> Enum.reduce(1, fn x, y -> div(x * y, Integer.gcd(x, y)) end)
IO.inspect steps
