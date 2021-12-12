package agh.ics.oop.EnumClasses;

public enum MoveDirection {
    FORWARD,
    FORWARDRIGHT,
    RIGHT,
    BACKWARDRIGHT,
    BACKWARD,
    BACKWARDLEFT,
    LEFT,
    FORWARDLEFT
}
/*
                 0*
       315       ↑       45
         \       |       /
          \    FORWARD  /
   FORWARDLEFT   |   FORWARDRIGHT
            \    |    /
             \   |   /
270 <--LEFT--  ANIMAL --RIGHT--> 90
             /   |   \
            /    |    \
  BACKWARDLEFT   |     BACKWARDRIGHT
          /   BACKWARD  \
         /       |       \
       225       ↓       135
                180*

 * - ANIMAL does not rotate into this direction

 */