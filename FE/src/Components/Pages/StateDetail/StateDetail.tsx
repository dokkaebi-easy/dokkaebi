import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import CancelIcon from '@mui/icons-material/Cancel';
import AutorenewIcon from '@mui/icons-material/Autorenew';
import PersonIcon from '@mui/icons-material/Person';
import { AiOutlineGitlab } from 'react-icons/ai';
import Typography from '@mui/material/Typography';
import styled from '@emotion/styled';
import Paper from '@mui/material/Paper';
import { useLocation, useParams } from 'react-router-dom';
import axios from 'axios';

interface GitInfo {
  gitBranch: string;
  gitRepositoryUrl: string;
  username: string;
}

interface StateInfo {
  buildNumber: number;
  consoleLog: string;
  gitInfo: GitInfo;
  projectId: number;
  projectName: string;
  registDate: string;
  stateType: string;
}

export default function StateDetail() {
  const [stateData, setStateData] = useState<StateInfo>({
    buildNumber: 0,
    consoleLog: '',
    gitInfo: { gitBranch: '', gitRepositoryUrl: '', username: '' },
    projectId: 0,
    projectName: '',
    registDate: '',
    stateType: '',
  });
  const location = useLocation();
  const params = useParams();

  useEffect(() => {
    const data = { ...params };
    axios.post(`/api/project/build/detail`, data).then((res) => {
      setStateData(res.data);
    });
  }, []);

  return (
    <Box
      mt={3}
      sx={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'left',
        padding: 5,
      }}
    >
      <Grid container direction="column" justifyContent="left">
        <Grid item display="flex">
          {stateData.stateType === 'Done' ? (
            <CheckCircleOutlineIcon color="success" sx={{ fontSize: 60 }} />
          ) : (
            <CancelIcon color="error" sx={{ fontSize: 60 }} />
          )}
          <Typography variant="h2" sx={{ marginLeft: 4 }}>
            프로젝트 : {stateData.projectName}
          </Typography>
        </Grid>
        <Grid item display="flex">
          <Typography variant="h3" sx={{ marginLeft: 15 }}>
            빌드 #{stateData.buildNumber}
          </Typography>
        </Grid>

        {stateData.gitInfo ? (
          <>
            <Grid item display="flex" sx={{ m: 4 }}>
              <PersonIcon sx={{ fontSize: 35 }} />
              <Box sx={{ marginLeft: 3 }}>
                {stateData.gitInfo ? (
                  <Typography variant="h5">
                    {`사용자 ${stateData.gitInfo.username}님이 시작하셨습니다.`}
                  </Typography>
                ) : (
                  <Typography variant="h5">{' 사용자 없음 '}</Typography>
                )}
              </Box>
            </Grid>
            <Grid item display="flex" sx={{ my: 2, mx: 4 }}>
              <AiOutlineGitlab size={35} />
              <Box sx={{ marginLeft: 3 }}>
                <Typography>
                  랩 저장소 :
                  <a href="##" target="_blank">
                    {stateData.gitInfo
                      ? `${stateData.gitInfo.gitRepositoryUrl}`
                      : '비어 있습니다.'}
                  </a>
                </Typography>
                <Typography>
                  깃랩 브랜치 :
                  {stateData.gitInfo
                    ? `${stateData.gitInfo.gitBranch}`
                    : '비어 있습니다.'}
                </Typography>
              </Box>
            </Grid>
          </>
        ) : null}
        <Box>
          <Typography variant="h4" sx={{ marginY: 3 }}>
            콘솔 로그
          </Typography>
          <Box
            sx={{
              width: 'max',
              paddingLeft: '5px',
              whiteSpace: 'pre-wrap',
              backgroundColor: '#84898c',
              border: ' 5px #020202',
              borderRadius: '10px',
            }}
          >
            {stateData.consoleLog}
          </Box>
        </Box>
      </Grid>
    </Box>
  );
}
