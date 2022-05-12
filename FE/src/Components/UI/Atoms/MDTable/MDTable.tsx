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
import { useRunStore } from 'Components/Store/RunStore/RunStore';
import Brightness2Icon from '@mui/icons-material/Brightness2';
import Brightness5Icon from '@mui/icons-material/Brightness5';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import Typography from '@mui/material/Typography';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    backgroundColor: theme.palette.common.white,

    fontSize: 15,
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
  const setRun = useRunStore((state) => state.setRun);
  const [projects, setProject] = useState<Project[]>([]);
  const [loading, setLoading] = React.useState(false);

  const handleClick = (projectId: number) => {
    setLoading(true);
    setRun(1);

    const params = { projectId };
    axios
      .post('/api/project/build', null, { params })
      .then(() => {
        setLoading(false);
        setRun(0);
      })
      .catch(() => {
        setLoading(false);
        setRun(2);
      });
  };

  useEffect(() => {
    axios
      .get('/api/project/all')
      .then((res) => {
        const data = res.data as Project[];
        setProject([...data]);
      })
      .catch((error) => {
        console.log(error);
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
            <StyledTableCell align="center">Edit</StyledTableCell>
            <StyledTableCell align="center">Del</StyledTableCell>
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
                      color: 'white',
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
                    <IconButton
                      color="primary"
                      aria-label="upload picture"
                      component="span"
                      sx={{ color: 'black' }}
                    >
                      {row.state === 'Failed' ? (
                        <Brightness5Icon sx={{ color: 'red' }} />
                      ) : (
                        <Brightness5Icon sx={{ color: 'red' }} />
                      )}
                    </IconButton>
                  </Link>
                </StyledTableCell>

                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.lastSuccessDate}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.lastFailDate}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Button
                    size="small"
                    sx={{
                      background: 'linear-gradient(195deg, #42424a, #191919)',
                    }}
                    variant="contained"
                  >
                    <EditIcon fontSize="small" sx={{ color: 'white' }} />
                  </Button>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Button
                    size="small"
                    sx={{
                      background: 'linear-gradient(195deg, #42424a, #191919)',
                    }}
                    variant="contained"
                  >
                    <DeleteIcon fontSize="small" sx={{ color: 'white' }} />
                  </Button>
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
