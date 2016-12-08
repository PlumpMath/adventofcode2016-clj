from collections import deque


def init_screen(screen, rect_str):
    rect_str = rect_str.split(' ')[-1]
    x, y = rect_str.strip().split('x')
    x, y = int(x), int(y)
    for i in range(y):
        for j in range(x):
            screen[i][j] = '#'


def rotate(screen, rot_str):
    s = rot_str.split()[1:]
    idx = int(s[1].split('=')[-1])
    by = int(s[-1])
    if s[0] == 'row':
        screen[idx].rotate(by)
    else:
        dq = deque([i[idx] for i in screen])
        dq.rotate(by)
        for i, j in zip(screen, dq):
            i[idx] = j


def count_pixels(screen):
    pixels = 0
    for row in screen:
        pixels += row.count('#')
    return pixels


if __name__ == '__main__':
    data = ""

    screen = [deque(50 * '.') for _ in range(6)]
    for row in data.split('\n'):
        row = row.strip()
        if not row:
            continue
        elif row.startswith('rect'):
            init_screen(screen, rect_str=row)
        else:
            rotate(screen, rot_str=row)

    print(count_pixels(screen))
    for row in screen:
        print(''.join(row))
