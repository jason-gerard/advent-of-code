counter = 0

with open('../src/main/scala/Day1/input.txt') as f:
    lines = [int(line.rstrip()) for line in f.readlines()]
    curr_sum = lines[:3]
    for line in lines[3:]:
        prev_sum = sum(curr_sum)

        # new sum
        curr_sum.pop(0)
        curr_sum.append(line)

        if sum(curr_sum) > prev_sum:
            counter += 1

print(counter)
