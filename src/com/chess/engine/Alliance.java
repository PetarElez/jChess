package com.chess.engine;

public enum Alliance {
    WHITE {
        @Override
        public int getDiresction() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    },
    BLACK {
        @Override
        public int getDiresction() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    };

    public abstract int getDiresction();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

}
