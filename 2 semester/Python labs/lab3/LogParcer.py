import re


if __name__ == "__main__":

    pattern = r"((72\.30\.79\.46)|(90\.7\.158\.134)).*GET.*(images).*(200)"
    number_of_matched_requests = 0
    read_lines = 0

    with open('access') as file:
        for line in file:
            read_lines += 1
            if re.match(pattern, line):
                number_of_matched_requests += 1
                print(line)

    print("\nNumber of all read lines: %d" % read_lines)
    print("\nNumber of all matched lines: %d" % number_of_matched_requests)
