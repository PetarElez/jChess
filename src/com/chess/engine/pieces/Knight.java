package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES={-17,-15,-10,-6,6,10,15,17};

    Knight(final int piecePosition,final Alliance pieceAllience){
        super(piecePosition,pieceAllience);
    }
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        int candidatDestinationCoordinate;
        final List<Move> legalMoves=new ArrayList<>();

        for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATES){
            candidatDestinationCoordinate=this.piecePosition+currentCandidateOffset;

            if(BoardUtils.isValidTileCoordinate(candidatDestinationCoordinate) ){

                if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset) ||
                        isSecondColumnExclusion(this.piecePosition,currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition,currentCandidateOffset) ||
                        isEightColumnExclusion(this.piecePosition,currentCandidateOffset) ){
                    continue;
                }

                final Tile candidateDestinationTile=board.getTile(candidatDestinationCoordinate);

                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board,this,candidatDestinationCoordinate));

                }else{
                    final Piece pieceAtDestination=candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAlliance=pieceAtDestination.getPieceAllience();

                    if(this.pieceAlliance != pieceAtDestinationAlliance){
                        legalMoves.add(new AttackMove(board,this,candidatDestinationCoordinate,pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset==-17) || (candidateOffset==-10)
        || candidateOffset==6|| candidateOffset==15);
    }

    private static boolean isSecondColumnExclusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset==-10) || candidateOffset==6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset==-6) || candidateOffset==10);
    }

    private static boolean isEightColumnExclusion(final int currentPosition,final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && ((candidateOffset==-15) || (candidateOffset==-6)
                || candidateOffset==10|| candidateOffset==17);
    }


}
