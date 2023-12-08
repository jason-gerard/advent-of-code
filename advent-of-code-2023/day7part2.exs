{:ok, content} = File.read("inputs/day7.txt")
lines = content |> String.split("\n", trim: true)

hands = lines |> Enum.map(fn line -> String.split(line, " ", trim: true) end)
IO.inspect hands

hand_type = hands |> Enum.map(fn [hand, bid] -> 
  freqs = hand
  |> String.graphemes
  |> Enum.frequencies
  |> Map.to_list
  IO.inspect freqs

  num_j = freqs |> Enum.find_value(0, fn {k, v} -> if k == "J", do: v end)
  IO.inspect num_j
  
  IO.inspect hand
  res = freqs |> Enum.sort_by(fn {k, v} -> if k == "J", do: v, else: -v end)
  {card1, count1} = res |> Enum.at(0)
  {_, count2} = res |> Enum.at(1, {"", 0})
  IO.inspect count1
  count1 = if card1 != "J" do
    count1 + num_j
  else
    count1
  end
  IO.inspect count1
  
  type = case {count1, count2} do
    {1, _} -> 1 # :high_card
    {2, 2} -> 3 # :two_pair
    {2, _} -> 2 # :two_kind
    {3, 2} -> 5 # :full_house
    {3, _} -> 4 # :three_kind
    {4, _} -> 6 # :four_kind
    {5, _} -> 7 # :five_kind
  end
  IO.inspect type
  [hand, bid, type]
end)
IO.inspect(hand_type, charlists: :as_lists)

card_strengths = %{
  "A" => 14,
  "K" => 13,
  "Q" => 12,
  "T" => 10,
  "9" => 9,
  "8" => 8,
  "7" => 7,
  "6" => 6,
  "5" => 5,
  "4" => 4,
  "3" => 3,
  "2" => 2,
  "J" => 1,
}

ordered_hands = hand_type |> Enum.sort(fn [hand1, _, type1], [hand2, _, type2] -> 
  if type1 == type2 do
    hand1 = hand1 |> String.split("", trim: true)
    hand2 = hand2 |> String.split("", trim: true)
    {card1, card2} = Enum.zip(hand1, hand2) |> Enum.filter(fn {h1, h2} -> h1 != h2 end) |> Enum.at(0)
    card1 = Map.get(card_strengths, card1)
    card2 = Map.get(card_strengths, card2)

    card1 < card2
  else
    type1 < type2
  end
end)
IO.inspect(ordered_hands, charlists: :as_lists)

winnings = ordered_hands
|> Enum.with_index(1)
|> Enum.map(fn {[_, bid, _], index} -> (Integer.parse(bid) |> elem(0)) * index end)
|> Enum.sum
IO.inspect winnings