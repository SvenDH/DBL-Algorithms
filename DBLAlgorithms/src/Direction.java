class Direction {
    static int N = 0;
    static int S = 1;
    static int W = 2;
    static int E = 3;
    
    //Get opposite direction
    public int opposite (int dir) {
        switch (dir) {
            case 0: return 1;
            case 1: return 0;
            case 2: return 3;
            case 3: return 2;
        }
        return -1;
    }
}
