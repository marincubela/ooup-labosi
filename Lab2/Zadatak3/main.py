def mymax(iterable, key=lambda x: x):
    first = True
    max_x = max_key = None

    for x in iterable:
        if first:
            first = False
            max_x = x
            max_key = key(x)
        if key(x) > max_key:
            max_x = x
            max_key = key(x)

    return max_x


def glavni():
    maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
    maxchar = mymax("Suncana strana ulice")
    maxstring = mymax(["Gle", "malu", "vocku", "poslije",
                       "kise", "Puna", "je", "kapi", "pa", "ih", "njise"])
    D = {'burek': 8, 'buhtla': 5}
    maxItem = mymax(D, D.get)
    maxpair = mymax([("Ivo", "Ivic"), ("Ana", "Anic"),
                     ("Marko", "Markic"), ("Stipe", "Bradaric")])

    print(maxint)
    print(maxchar)
    print(maxstring)
    print(maxItem)
    print(maxpair)


if __name__ == "__main__":
    glavni()
