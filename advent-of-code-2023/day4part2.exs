{:ok, content} = File.read("inputs/day4.txt")
lines = content |> String.split("\n", trim: true)

card_pairs = lines |> Enum.map(fn line ->
  line
  |> String.split(":", trim: true)
  |> Enum.at(1)
  |> String.split("|", trim: true)
  |> Enum.map(fn part -> part |> String.split(" ", trim: true) end)
end)

IO.inspect card_pairs

winning_card = card_pairs |> Enum.map(fn pair -> 
  winning_cards = pair |> Enum.at(0)
  pair
  |> Enum.at(1)
  |> Enum.filter(fn card -> card in winning_cards end)
end)

IO.inspect winning_card

winning_card_counts = winning_card |> Enum.map(&length(&1))

IO.inspect winning_card_counts

defmodule Solution do
  def compute_score(cards, acc, winning_card_counts) do
    if cards == [] do
      acc
    else
      card_index = hd(cards)
      num_won_cards = winning_card_counts |> Enum.at(card_index)
      won_cards = if num_won_cards > 0 do Enum.to_list((card_index + 1)..(card_index + num_won_cards)) else [] end
      
      compute_score(won_cards ++ tl(cards), acc+1, winning_card_counts)
    end
  end
end

sol = Solution.compute_score(Enum.to_list(0..length(winning_card_counts)-1), 0, winning_card_counts)
IO.inspect sol