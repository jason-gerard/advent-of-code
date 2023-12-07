{:ok, content} = File.read("inputs/day6.txt")
lines = content |> String.split("\n", trim: true)

[duration, best_distance] = lines |> Enum.map(fn line ->
  line |> String.split(":", trim: true) |> Enum.at(1) |> String.replace(" ", "") |> Integer.parse |> elem(0)
end)

first_win = 0..duration |> Enum.find(fn btn_duration -> (duration - btn_duration) * btn_duration > best_distance end)
last_win = duration..0 |> Enum.find(fn btn_duration -> (duration - btn_duration) * btn_duration > best_distance end)

IO.inspect last_win - first_win + 1
