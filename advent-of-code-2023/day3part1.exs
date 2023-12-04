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
      if point in symbols do
        # IO.inspect "#{i}, #{j}, #{point}"
        # west, southwest, south, southeast, east, northeast, north, northwest
        [[i, j-1], [i+1, j-1], [i+1, j], [i+1, j+1], [i, j+1], [i-1, j+1], [i-1, j], [i-1, j-1]]
      else
        []
      end
    end)
  end)
  |> Enum.uniq

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

part_numbers = parts
  |> Enum.map(fn part -> part |> Map.get(:value) |> Integer.parse |> elem(0) end)
  |> Enum.sum

IO.inspect part_numbers