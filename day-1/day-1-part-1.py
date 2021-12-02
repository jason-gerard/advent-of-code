counter = 0

with open('input.txt') as f:
    lines = f.readlines()
    prev = -1
    for line in lines:
        curr = int(line.rstrip())
        if prev != -1 and curr > prev:
            counter += 1

        prev = curr

print(counter)
