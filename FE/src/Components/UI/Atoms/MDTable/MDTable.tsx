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
import axios from 'axios';
import { Link } from 'react-router-dom';

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

export default function MDTable() {
  const [projects, setProject] = useState<Project[]>([]);

  useEffect(() => {
    axios.get('/api/project/all').then((res) => {
      const data = res.data as Project[];
      setProject([...data]);
    });
  }, []);

  return (
    <TableContainer component={Paper} sx={{ borderRadius: 3 }}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell align="center">Project ID</StyledTableCell>
            <StyledTableCell align="center">Name</StyledTableCell>
            <StyledTableCell align="center">S</StyledTableCell>
            <StyledTableCell align="center">W </StyledTableCell>
            <StyledTableCell align="right">최근성공</StyledTableCell>
            <StyledTableCell align="right">최근 실패</StyledTableCell>
            <StyledTableCell align="right">최근 소요 시간</StyledTableCell>
          </TableRow>
        </TableHead>
        {projects ? (
          <TableBody>
            {projects.map((row) => (
              <StyledTableRow key={uuid()}>
                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.projectId}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center" component="th" scope="row">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.projectName}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.state}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.state}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="right">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.state}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="right">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.state}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="right">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.state}
                  </Link>
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        ) : (
          <TableBody />
        )}
      </Table>
    </TableContainer>
  );
}
