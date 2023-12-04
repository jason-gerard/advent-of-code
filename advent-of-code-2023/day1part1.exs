{:ok, content} = File.read("inputs/day1.txt")
lines = content |> String.split("\n", trim: true)

count = lines 
        |> Enum.map(fn line -> line
                               |> String.replace(~r/[^\d]/, "")
                               |> String.split("", trim: true) end)
        |> Enum.map(fn numbers -> "#{hd(numbers)}#{List.last(numbers)}" end)
        |> Enum.map(&Integer.parse(&1) |> elem(0))
        |> Enum.sum

IO.inspect count