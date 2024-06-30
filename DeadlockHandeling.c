#include <stdio.h>
#include <stdbool.h>

#define NUM_PROCESSES 5
#define NUM_RESOURCES 3

bool is_safe(int processes[], int avail[], int maxm[][NUM_RESOURCES], int allot[][NUM_RESOURCES], int safe_seq[])
{
    int work[NUM_RESOURCES];
    bool finish[NUM_PROCESSES] = {false};

    // Copy available resources to work
    for (int i = 0; i < NUM_RESOURCES; i++)
    {
        work[i] = avail[i];
    }

    int count = 0;

    while (count < NUM_PROCESSES)
    {
        bool found = false;
        for (int p = 0; p < NUM_PROCESSES; p++)
        {
            if (!finish[p])
            {
                int j;
                for (j = 0; j < NUM_RESOURCES; j++)
                {
                    if (maxm[p][j] - allot[p][j] > work[j])
                        break;
                }

                if (j == NUM_RESOURCES)
                {
                    for (int k = 0; k < NUM_RESOURCES; k++)
                    {
                        work[k] += allot[p][k];
                    }

                    safe_seq[count++] = processes[p];
                    finish[p] = true;
                    found = true;
                }
            }
        }

        if (!found)
        {
            return false;
        }
    }

    return true;
}

int main()
{
    int processes[] = {0, 1, 2, 3, 4};

    // Available instances of resources
    int avail[] = {3, 3, 2};

    // Maximum R that can be allocated to processes
    int maxm[][NUM_RESOURCES] = {
        {7, 5, 3},
        {3, 2, 2},
        {9, 0, 2},
        {2, 2, 2},
        {4, 3, 3}};

    // Resources allocated to processes
    int allot[][NUM_RESOURCES] = {
        {0, 1, 0},
        {2, 0, 0},
        {3, 0, 2},
        {2, 1, 1},
        {0, 0, 2}};

    int safe_seq[NUM_PROCESSES];

    if (is_safe(processes, avail, maxm, allot, safe_seq))
    {
        printf("System is in a safe state.\nSafe sequence is: ");
        for (int i = 0; i < NUM_PROCESSES; i++)
        {
            printf("%d ", safe_seq[i]);
        }
        printf("\n");
    }
    else
    {
        printf("System is not in a safe state.\n");
    }

    return 0;
}