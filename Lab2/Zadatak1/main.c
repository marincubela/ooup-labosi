#include <stdio.h>
#include <string.h>

const void *mymax(
    const void *base, size_t nmemb, size_t size,
    int (*compar)(const void *, const void *)) {
    void *max = &base[0];
    for (int i = 0; i < nmemb; i++) {
        if (compar(base + i * size, max)) {
            max = base + i * size;
        }
    }

    return max;
}

int gt_int(const void *a, const void *b) {
    if (*(int *)a > *(int *)b) {
        return 1;
    } else {
        return 0;
    }
}

int gt_char(const void *a, const void *b) {
    if (*(char *)a > *(char *)b) {
        return 1;
    } else {
        return 0;
    }
}

int gt_str(const void *a, const void *b) {
    if (strcmp(*(char **)a, *(char **)b) > 0) {
        return 1;
    } else {
        return 0;
    }
}

int main(void) {
    int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
    char arr_char[] = "Suncana strana ulice";
    const char *arr_str[] = {
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"};

    int max = *(int *)mymax(arr_int, 9, sizeof(int), gt_int);
    printf("Najveci integer: %d\n", max);

    char maxSlovo = *(char *)mymax(arr_char, 20, sizeof(char), gt_char);
    printf("Najvece slovo: %c\n", maxSlovo);

    char *maxNiz = *(char **)mymax(arr_str, 11, sizeof(const char *), gt_str);
    printf("Najveci niz: %s\n", maxNiz);

    return 0;
}