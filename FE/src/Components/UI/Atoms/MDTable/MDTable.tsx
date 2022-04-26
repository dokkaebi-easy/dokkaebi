import React, { useEffect, useState } from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import ProjectDatas, {
  Project,
} from 'Components/MDClass/ProjectData/ProjectData';
import { v4 as uuid } from 'uuid';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

function createData(
  name: string,
  succes: number,
  fail: number,
  time: number,
  build: number,
  state: number,
) {
  return { name, succes, fail, time, build, state };
}

// const rows = [
//   createData('Frozen yoghurt', 159, 6.0, 24, 4.0, 1),
//   createData('Ice cream sandwich', 237, 9.0, 37, 4.3, 1),
//   createData('Eclair', 262, 16.0, 24, 6.0, 1),
//   createData('Cupcake', 305, 3.7, 67, 4.3, 1),
//   createData('Gingerbread', 356, 16.0, 49, 3.9, 1),
// ];

interface TableProps {
  rows: Project[];
}

export default function MDTable({ rows }: TableProps) {
  return (
    <TableContainer component={Paper} sx={{ borderRadius: 3 }}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>Name</StyledTableCell>
            <StyledTableCell align="right">최근성공</StyledTableCell>
            <StyledTableCell align="right">최근 실패</StyledTableCell>
            <StyledTableCell align="right">최근 소요 시간</StyledTableCell>
            <StyledTableCell align="right">S</StyledTableCell>
            <StyledTableCell align="right">W </StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <StyledTableRow key={uuid()}>
              <StyledTableCell component="th" scope="row">
                {row.projectName}
              </StyledTableCell>
              <StyledTableCell align="right">{row.projectName}</StyledTableCell>
              <StyledTableCell align="right">{row.projectName}</StyledTableCell>
              <StyledTableCell align="right">{row.state}</StyledTableCell>
              <StyledTableCell align="right">{row.state}</StyledTableCell>
              <StyledTableCell align="right">{row.state}</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
