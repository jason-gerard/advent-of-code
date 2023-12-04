{:ok, content} = File.read("inputs/day1.txt")
lines = content |> String.split("\n", trim: true)

string_to_number = %{
  "one" => "1",
  "two" => "2",
  "three" => "3",
  "four" => "4",
  "five" => "5",
  "six" => "6",
  "seven" => "7",
  "eight" => "8",
  "nine" => "9",
}

filter_line = fn (line) ->
  split = String.split(line, "", trim: true)
  
  first = Enum.reduce_while(split, "", fn char, acc ->
    next_word = "#{acc}#{char}"
    
    case Integer.parse(char) do
      {_, ""} -> {:halt, char}
      _ -> 
        mapping = Enum.find(Map.keys(string_to_number), fn str -> String.contains?(next_word, str) end)
        if mapping do
          {:halt, Map.get(string_to_number, mapping)}
        else
          {:cont, next_word} 
        end
    end
  end)
  
  last = split
    |> Enum.reverse
    |> Enum.reduce_while("", fn char, acc ->
    next_word = "#{char}#{acc}"

    case Integer.parse(char) do
      {_, ""} -> {:halt, char}
      _ ->
        mapping = Enum.find(Map.keys(string_to_number), fn str -> String.contains?(next_word, str) end)
        if mapping do
          {:halt, Map.get(string_to_number, mapping)}
        else
          {:cont, next_word}
        end
    end
  end)
  
  "#{first}#{last}"
end

count = lines
        |> Enum.map(&filter_line.(&1))
        |> Enum.map(&Integer.parse(&1) |> elem(0))
        |> Enum.sum

IO.inspect count
