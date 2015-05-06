class Direction {
    static final int N = 0;
    static final int S = 1;
    static final int W = 2;
    static final int E = 3;
    
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
