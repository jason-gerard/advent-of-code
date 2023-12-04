{:ok, content} = File.read("inputs/day4.txt")
lines = content |> String.split("\n", trim: true)

card_pairs = lines |> Enum.map(fn line ->
  line
  |> String.split(":", trim: true)
  |> Enum.at(1)
  |> String.split("|", trim: true)
  |> Enum.map(fn part -> part |> String.split(" ", trim: true) end)
end)

winning_card = card_pairs |> Enum.map(fn pair -> 
  winning_cards = pair |> Enum.at(0)
  pair
  |> Enum.at(1)
  |> Enum.filter(fn card -> card in winning_cards end)
end)

winning_card_counts = winning_card 
  |> Enum.map(&length(&1))
  |> Enum.filter(&(&1 > 0))
  |> Enum.map(&Integer.pow(2, &1 - 1))
  |> Enum.sum

IO.inspect winning_card_counts