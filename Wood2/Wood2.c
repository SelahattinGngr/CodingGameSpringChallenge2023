#include <stdio.h>
#include <stdlib.h>

#define T_EGG 1
#define T_CRY 2

#define B_AL 1
#define B_EN 2

typedef struct {
    int index;
    int type;
    int start_amount;
    int amount;
    int base;
    int beacon_strength;
    int ally_ants;
    int enemy_ants;
    int neighbors[6];
} Cell;

typedef struct {
    char const* message;
} GenericOuafException;

void Debug(Cell* cells, int size) {
    for (int i = 0; i < size; i++) {
        printf("CELL #%d | index: %d | type: %d | start_amount: %d | current amount: %d\n", i, cells[i].index, cells[i].type, cells[i].start_amount, cells[i].amount);
        printf("base: %d | beacon: %d | ally: %d | enemy: %d\n", cells[i].base, cells[i].beacon_strength, cells[i].ally_ants, cells[i].enemy_ants);
        printf("neighbors: ");
        for (int j = 0; j < 6; j++) {
            printf("#%d: %d | ", j, cells[i].neighbors[j]);
        }
        printf("\n");
    }
}

int is_type(Cell* cell, int type) {
    return cell->type == type;
}

int is_viable(Cell* cell) {
    return cell->start_amount > 0 && cell->amount > 0;
}

Cell get_base(Cell* cells, int size) {
    for (int i = 0; i < size; i++) {
        if (cells[i].base == 1) {
            return cells[i];
        }
    }
    GenericOuafException exception = { "OUAF." };
    printf("%s\n", exception.message);
    exit(1);
}

Cell get_target(Cell* cells, int size, int type) {
    for (int i = 0; i < size; i++) {
        if (is_type(&cells[i], type) && is_viable(&cells[i])) {
            return cells[i];
        }
    }
    GenericOuafException exception = { "OUAF." };
    printf("%s\n", exception.message);
    exit(1);
}

void init_cells(Cell* cells, int size) {
    for (int i = 0; i < size; i++) {
        Cell tmp;

        int value;
        scanf("%d", &value); //type
        tmp.type = value;
        scanf("%d", &value); //initial resources
        tmp.start_amount = value;
        for (int j = 0; j < 6; j++) //neighbours
        {
            scanf("%d", &value);
            tmp.neighbors[j] = value;
        }
        getchar();
        tmp.index = i;
        cells[i] = tmp;
    }
}

void init_current_turn(Cell* cells, int size) {
    int nb_of_ally = 0;
    int nb_of_enemy = 0;
    for (int i = 0; i < size; i++) {
        int value;

        scanf("%d", &value);
        cells[i].amount = value;
        scanf("%d", &value);
        cells[i].ally_ants = value;
        nb_of_ally += value;
        scanf("%d", &value);
        getchar();
        cells[i].enemy_ants = value;
        nb_of_enemy += value;
    }
}

void print_line(Cell base, Cell target, int strength) {
    printf("LINE %d %d %d;", base.index, target.index, strength);
}

void print_beacon(Cell target, int strength) {
    printf("BEACON %d %d", target.index, strength);
}

int main() {
    int Cell_nb_of_cells;
    scanf("%d", &Cell_nb_of_cells);
    getchar();

    Cell* cells = (Cell*)malloc(Cell_nb_of_cells * sizeof(Cell));
    init_cells(cells, Cell_nb_of_cells);

    int Cell_nb_of_bases;
    scanf("%d", &Cell_nb_of_bases);
    getchar();
    for (int i = 1; i < 3; i++) {
        int index;
        scanf("%d", &index);
        getchar();
        for (int j = 0; j < Cell_nb_of_cells; j++) {
            if (index == cells[j].index) {
                cells[j].base = i;
            }
        }
    }

    // game loop
    while (1) {
        Cell base;
        Cell target;

        init_current_turn(cells, Cell_nb_of_cells);
        base = get_base(cells, Cell_nb_of_cells);
        // Egg Target
        int hasEggTarget = 0;
        for (int i = 0; i < Cell_nb_of_cells; i++) {
            if (is_type(&cells[i], T_EGG) && is_viable(&cells[i])) {
                hasEggTarget = 1;
                print_line(base, cells[base.base+i], 2);
                print_line(base, cells[base.base+i+1], 1);
                break;
            }
        }

        // Crystal Target
        int hasCrystalTarget = 0;
        for (int i = 0; i < Cell_nb_of_cells; i++) {
            if (is_type(&cells[i], T_CRY) && is_viable(&cells[i])) {
                hasCrystalTarget = 1;
                print_line(base, cells[base.base +i], 1);
                print_line(base, cells[base.base + i+1], 1);
                printf("\n");
                break;
            }
        }

        if (!hasEggTarget && !hasCrystalTarget) {
            break;
        }
    }

    free(cells);
    return 0;
}

