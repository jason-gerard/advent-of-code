{:ok, content} = File.read("inputs/day3.txt")
lines = content |> String.split("\n", trim: true)

symbols = ["*", "#", "+", "$", "=", "/", "+", "%", "@", "-", "&"]

points = lines |> Enum.map(&String.split(&1, "", trim: true))
IO.inspect points

symbol_indices = points
  |> Enum.with_index
  |> Enum.flat_map(fn {row, i} ->
    row
    |> Enum.with_index
    |> Enum.flat_map(fn {point, j} ->
      if point == "*" do
        # IO.inspect "#{i}, #{j}, #{point}"
        # west, southwest, south, southeast, east, northeast, north, northwest
        [[i, j-1], [i+1, j-1], [i+1, j], [i+1, j+1], [i, j+1], [i-1, j+1], [i-1, j], [i-1, j-1]]
      else
        []
      end
    end)
  end)
  |> Enum.uniq

IO.inspect symbol_indices

points_with_index = points |> Enum.with_index |> Enum.flat_map(fn {row, i} ->
  row |> Enum.with_index |> Enum.map(fn {point, j} ->
    %{
      :value => point,
      :index => [[i, j]]
    }
  end) end)

point_sets = points_with_index
  |> Enum.reduce([%{}], fn point, acc ->
    curr_set = hd(acc)
    value = point |> Map.get(:value)
    
    cond do
      value in symbols or value == "." -> [%{} | acc]
      curr_set == %{} -> [point | acc] 
      true ->
        new_set = curr_set
          |> Map.update!(:value, &("#{&1}#{value}"))
          |> Map.update!(:index, &(Map.get(point, :index) ++ &1))

        [new_set | tl(acc)]
    end
  end)
  |> Enum.filter(fn point_set -> point_set != %{} end)
  |> Enum.reverse

point_sets |> IO.inspect(charlists: :as_lists)

parts = point_sets |> Enum.filter(fn point_set ->
  point_set |> Map.get(:index) |> Enum.any?(fn point ->
    point in symbol_indices
  end)
end)

parts |> IO.inspect(charlists: :as_lists)

gear_ratios = points
 |> Enum.with_index
 |> Enum.flat_map(fn {row, i} ->
  row
  |> Enum.with_index
  |> Enum.map(fn {point, j} ->
    if point == "*" do
      # west, southwest, south, southeast, east, northeast, north, northwest
      indices = [[i, j-1], [i+1, j-1], [i+1, j], [i+1, j+1], [i, j+1], [i-1, j+1], [i-1, j], [i-1, j-1]]
      matching_parts = parts |> Enum.filter(fn part ->
        part |> Map.get(:index) |> Enum.any?(fn point ->
          point in indices
        end)
      end)
      matching_parts |> IO.inspect(charlists: :as_lists)
      if length(matching_parts) == 2 do
        part_numbers = matching_parts |> Enum.map(fn part -> part |> Map.get(:value) |> Integer.parse |> elem(0) end)
        part_numbers |> IO.inspect(charlists: :as_lists)
        Enum.at(part_numbers, 0) * Enum.at(part_numbers, 1)
      else
        0
      end
    else
      0
    end
  end)
end)

gear_ratios |> Enum.sum |> IO.inspect(charlists: :as_lists)
