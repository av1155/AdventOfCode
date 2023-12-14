import re

# Define the mapping of words to digits
digit_map = {
    "one": "1",
    "two": "2",
    "three": "3",
    "four": "4",
    "five": "5",
    "six": "6",
    "seven": "7",
    "eight": "8",
    "nine": "9",
}


# Function to convert word to digit if needed
def word_to_digit(word):
    return digit_map.get(word, word)


# Read the file
file_path = "/Users/andreaventi/Developer/AdventOfCode/src/year_2023/day_1/input.txt"
with open(file_path) as file:
    lines = file.readlines()

# First Part: Sum of first and last digits
first_last_sum = sum(
    int(line[0] + line[-1]) for line in [re.findall("\\d", line) for line in lines]
)

# Second Part: Sum with word to digit conversion
converted_sum = sum(
    int(word_to_digit(v[0]) + word_to_digit(v[-1]))
    for line in lines
    for v in [re.findall("\\d|one|two|three|four|five|six|seven|eight|nine", line)]
)

print(first_last_sum)
print(converted_sum)
