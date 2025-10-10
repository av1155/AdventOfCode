from pathlib import Path


def fileContents():
    PWD = Path(__file__).resolve().parent  # 2023/day01/python
    PARENT_DIR = PWD.parent  # 2023/day01

    with open(f"{PARENT_DIR}/input.txt", "r") as file:
        file_content = file.read()

    return file_content


def findCalibrationValues(text: str):
    lines = text.splitlines()

    total_calibration_values_sum = 0
    for line in lines:

        first_digit = 0
        for char in line:
            if char.isdigit():
                first_digit = char
                break

        last_digit = 0
        for char in reversed(line):
            if char.isdigit():
                last_digit = char
                break

        line_calibration_values_concatenation = f"{first_digit}{last_digit}"
        total_calibration_values_sum += int(line_calibration_values_concatenation)

    return total_calibration_values_sum

    # calibration_lines_list = text.splitlines()
    #
    # total_calibration_values_sum = 0
    # for element in calibration_lines_list:
    #
    #     first_digit = 0
    #     first_digit_index = None
    #     for first_digit_index, char in enumerate(element):
    #         if char.isdigit():
    #             first_digit = int(char)
    #             break
    #
    #     last_digit = 0
    #     for index, char in enumerate(reversed(element)):
    #         element_length = len(element)
    #         last_digit_index = (element_length - 1) - index
    #         if char.isdigit() and last_digit_index != first_digit_index:
    #             last_digit = int(char)
    #             break
    #
    #     element_calibration_values_sum = str(first_digit) + str(last_digit)
    #     total_calibration_values_sum += int(element_calibration_values_sum)
    #
    # return total_calibration_values_sum


def main():
    print(findCalibrationValues(fileContents()))


if __name__ == "__main__":
    main()
