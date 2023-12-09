{:ok, content} = File.read("inputs/day9.txt")
lines = content |> String.split("\n", trim: true)

readings = lines |> Enum.map(&String.split(&1, " ", trim: true) |> Enum.map(fn reading -> Integer.parse(reading) |> elem(0) end))
IO.inspect readings

defmodule Solution do
  def reduce_seq(sequences) do
    if hd(sequences) |> Enum.all?(&(&1 == 0)) do
      sequences
    else
      new_seq = hd(sequences)
      |> Enum.chunk_every(2, 1, :discard)
      |> Enum.map(fn [first, second] -> second - first end)
      
      reduce_seq([new_seq | sequences])
    end
  end
end

solution = readings
|> Enum.map(fn seq -> Solution.reduce_seq([seq]) end)
|> Enum.map(fn sequences ->
  sequences
  |> Enum.map(fn seq -> List.first(seq) end)
  |> Enum.reduce(fn reading, acc -> reading - acc end)
end)
|> Enum.sum
IO.inspect(solution, charlists: :as_lists)
