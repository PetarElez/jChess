package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVES_COORDINATES={8,16,7,9};

    Pawn(final int piecePosition,final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves=new ArrayList<>();

        for(final int currentCandidateOffset:CANDIDATE_MOVES_COORDINATES){
           final int candidateDestinatioinCoordiante=this.piecePosition+(this.pieceAlliance.getDiresction()*currentCandidateOffset);

            if(!BoardUtils.isValidTileCoordinate(candidateDestinatioinCoordiante)){
                continue;
            }

            if(currentCandidateOffset==8 && !board.getTile(candidateDestinatioinCoordiante).isTileOccupied()){
                legalMoves.add(new MajorMove(board,this,candidateDestinatioinCoordiante));
            }else if(currentCandidateOffset==16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAllience().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAllience().isWhite())){
                final int behindCandidateDestinationCoordinate=this.piecePosition+(this.getPieceAllience().getDiresction()*8);
                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                    !board.getTile(candidateDestinatioinCoordiante).isTileOccupied()){

                    legalMoves.add(new MajorMove(board,this,candidateDestinatioinCoordiante));

                }
            }else if(currentCandidateOffset==7 &&
                    !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()  ||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) ) ){
                if(board.getTile(candidateDestinatioinCoordiante).isTileOccupied()){
                    final Piece pieceOnCandidate=board.getTile(candidateDestinatioinCoordiante).getPiece();
                    if(this.pieceAlliance!=pieceOnCandidate.getPieceAllience()){
                        legalMoves.add(new MajorMove(board,this,candidateDestinatioinCoordiante));

                    }
                }

            }else if(currentCandidateOffset==9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()  ||
                     (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) )){
                if(board.getTile(candidateDestinatioinCoordiante).isTileOccupied()){
                    final Piece pieceOnCandidate=board.getTile(candidateDestinatioinCoordiante).getPiece();
                    if(this.pieceAlliance!=pieceOnCandidate.getPieceAllience()){
                        legalMoves.add(new MajorMove(board,this,candidateDestinatioinCoordiante));

                    }
                }

            }
        }


        return ImmutableList.copyOf(legalMoves);
    }
}
