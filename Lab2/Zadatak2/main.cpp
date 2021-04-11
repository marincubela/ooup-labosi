#include <stdio.h>
#include <string.h>

#include <algorithm>
#include <set>
#include <vector>

template <typename Iterator, typename Predicate>
Iterator mymax(
    Iterator first, Iterator last, Predicate pred) {
    Iterator max = first;
    while (first != last) {
        if (pred(*first, *max)) {
            max = first;
        }
        first++;
    }

    return max;
}

int gt_int(int a, int b) {
    return a > b ? 1 : 0;
}

int gt_char(char a, char b) {
    return a > b ? 1 : 0;
}

int gt_str(const char *a, const char *b) {
    return strcmp(a, b) > 0 ? 1 : 0;
}

int main(void) {
    int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
    char arr_char[] = "Suncana strana ulice";
    const char *arr_str[] = {
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"};
    std::vector<int> vec_int;
    vec_int.push_back(5);
    vec_int.push_back(7);
    vec_int.push_back(20);
    vec_int.push_back(15);

    std::set<char> set_char;
    set_char.insert('a');
    set_char.insert('z');
    set_char.insert('c');
    set_char.insert('M');

    int max = *mymax(&arr_int[0], &arr_int[sizeof(arr_int) / sizeof(*arr_int)], gt_int);
    printf("Najveci integer: %d\n", max);

    char maxSlovo = *mymax(&arr_char[0], &arr_char[sizeof(arr_char) / sizeof(*arr_char)], gt_char);
    printf("Najvece slovo: %c\n", maxSlovo);

    char *maxNiz = *(char **)mymax(&arr_str[0], &arr_str[sizeof(arr_str) / sizeof(*arr_str)], gt_str);
    printf("Najveci niz: %s\n", maxNiz);

    max = *mymax(vec_int.begin(), vec_int.end(), gt_int);
    printf("Najveci integer u vectoru: %d\n", max);

    maxSlovo = *mymax(set_char.begin(), set_char.end(), gt_char);
    printf("Najvece slovo u setu: %c\n", maxSlovo);

    return 0;
}