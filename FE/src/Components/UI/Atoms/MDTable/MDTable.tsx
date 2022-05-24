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
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import StopIcon from '@mui/icons-material/Stop';
import HourglassTopIcon from '@mui/icons-material/HourglassTop';

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

const WatiStyle = {
  background: 'linear-gradient(195deg, #42424a, #191919)',
};

const LodingStyle = {
  background: 'linear-gradient(195deg, #aaa, #191919)',
};

export default function MDTable() {
  const setRun = useRunStore((state) => state.setRun);
  const [projects, setProject] = useState<Project[]>([]);
  const [loading, setLoading] = React.useState(false);
  const [style, setStyle] = React.useState(WatiStyle);

  const reSet = () => {
    axios
      .get('/api/project/all')
      .then((res) => {
        const data = res.data as Project[];
        setProject([...data]);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleRunClick = (projectId: number) => {
    setLoading(true);
    setRun(1);
    setStyle(LodingStyle);
    const params = { projectId };
    axios
      .post('/api/project/build', null, { params })
      .then(() => {
        setLoading(false);
        setRun(0);
        reSet();
        setStyle(WatiStyle);
      })
      .catch(() => {
        setLoading(false);
        setRun(2);
        reSet();
        setStyle(WatiStyle);
      });
  };

  const handleStopClick = (projectId: number) => {
    axios.put(`/api/project/stop/${projectId}`).then(() => {
      reSet();
    });
  };

  const handleDelClick = (projectId: number) => {
    axios.delete(`/api/project/${projectId}`).then(() => {
      reSet();
    });
  };

  useEffect(() => {
    reSet();
  }, [loading]);

  useEffect(() => {
    reSet();

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
            <StyledTableCell align="center">빌드 정지</StyledTableCell>
            <StyledTableCell align="center">Project ID</StyledTableCell>
            <StyledTableCell align="center">Name</StyledTableCell>
            <StyledTableCell align="center">상태</StyledTableCell>
            <StyledTableCell align="center">포트</StyledTableCell>
            <StyledTableCell align="center">최근 실행</StyledTableCell>
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
                      ...style,
                      color: 'white',
                    }}
                    onClick={() => handleRunClick(row.projectId)}
                    loading={loading}
                    variant={loading ? 'outlined' : 'contained'}
                    disabled={loading}
                  >
                    {loading ? (
                      <HourglassTopIcon
                        fontSize="small"
                        sx={{
                          color: 'white',
                        }}
                      />
                    ) : (
                      <PlayArrowIcon
                        fontSize="small"
                        sx={{
                          color: 'white',
                        }}
                      />
                    )}
                  </LoadingButton>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Button
                    size="small"
                    sx={{
                      ...style,
                      color: 'white',
                      background: 'linear-gradient(195deg, #ee6666, #ff2222)',
                    }}
                    onClick={() => handleStopClick(row.projectId)}
                  >
                    <StopIcon
                      fontSize="small"
                      sx={{
                        color: 'white',
                      }}
                    />
                  </Button>
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
                    {row.state}
                  </Link>
                </StyledTableCell>

                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.ports.map((value) => {
                      return (
                        <div key={uuid()}>
                          {value.name} : {value.host}
                        </div>
                      );
                    })}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Link
                    to={`/detail/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    {row.recentBuildDate}
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Link
                    to={`/setting/${row.projectId}`}
                    style={{ color: 'black', textDecoration: 'none' }}
                  >
                    <Button
                      size="small"
                      sx={{
                        background: 'linear-gradient(195deg, #42424a, #191919)',
                      }}
                      variant="contained"
                    >
                      <EditIcon fontSize="small" sx={{ color: 'white' }} />
                    </Button>
                  </Link>
                </StyledTableCell>
                <StyledTableCell align="center">
                  <Button
                    size="small"
                    sx={{
                      background: 'linear-gradient(195deg, #ee6666, #ff2222)',
                    }}
                    variant="contained"
                    onClick={() => {
                      handleDelClick(row.projectId);
                    }}
                  >
                    <DeleteIcon
                      fontSize="small"
                      sx={{
                        color: 'white',
                      }}
                    />
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
