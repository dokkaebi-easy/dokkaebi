import React, { useEffect, useState } from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Project } from 'Components/MDClass/ProjectData/ProjectData';
import { v4 as uuid } from 'uuid';
import axios from 'axios';
import { Link } from 'react-router-dom';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import LoadingButton from '@mui/lab/LoadingButton';

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
  const [loading, setLoading] = React.useState(false);

  const handleClick = (projectId: number) => {
    setLoading(true);

    const params = { projectId };
    axios
      .post('/api/project/build', null, { params })
      .then(() => {
        setLoading(false);
      })
      .catch(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    axios.get('/api/project/all').then((res) => {
      const data = res.data as Project[];
      setProject([...data]);
    });

    return () => {
      setProject([]);
    };
  }, []);

  return (
    <TableContainer component={Paper} sx={{ borderRadius: 3 }}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell align="center">빌드 실행</StyledTableCell>
            <StyledTableCell align="center">Project ID</StyledTableCell>
            <StyledTableCell align="center">Name</StyledTableCell>
            <StyledTableCell align="center">S</StyledTableCell>
            <StyledTableCell align="center">최근성공</StyledTableCell>
            <StyledTableCell align="center">최근 실패</StyledTableCell>
            <StyledTableCell align="center">최근 소요 시간</StyledTableCell>
          </TableRow>
        </TableHead>
        {projects ? (
          <TableBody>
            {projects.map((row) => (
              <StyledTableRow key={uuid()}>
                <StyledTableCell align="center">
                  <LoadingButton
                    size="small"
                    sx={{
                      background: 'linear-gradient(195deg, #42424a, #191919)',
                    }}
                    onClick={() => handleClick(row.projectId)}
                    loading={loading}
                    variant={loading ? 'outlined' : 'contained'}
                    disabled={loading}
                    startIcon={<PlayArrowIcon />}
                  />
                </StyledTableCell>
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
                    진행중... (미완성)
                  </Link>
                </StyledTableCell>

                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    진행중... (미완성)
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    진행중... (미완성)
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    진행중... (미완성)
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
