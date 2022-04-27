import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import KeyboardTabIcon from '@mui/icons-material/KeyboardTab';
import PersonIcon from '@mui/icons-material/Person';
import { AiOutlineGitlab } from 'react-icons/ai';
import styled from '@emotion/styled';
import Paper from '@mui/material/Paper';
import { Typography } from '@mui/material';
import { api } from '../../../api/index';

const data = {
  projectName: 'doho123',
  buildNumber: 1,
  gitInfo: {
    username: 'dddd',
    gitRepositoryUrl: 'http://lab.ssafy.com/s06-final/s06-final/s06p31s205.git',
    gitBranch: 'dev',
  },
  consoleLog:
    'bb004ff7f98bc9f9a879c28a4eb78629284b8d5f0eb9a692fe8cdebe742d4b35\n"docker build" requires exactly 1 argument.\nSee \'docker build --help\'.\n\nUsage:  docker build [OPTIONS] PATH | URL | -\n\nBuild an image from a Dockerfile\n',
};

export default function BuildDetail() {
  // const [data, setData] = useState('');

  useEffect(() => {
    api
      .get(`project/build/detail?buildStateId=1&buildType=pull`)
      .then((res) => {
        console.log(res);
      })
      .catch((error) => {
        console.log(error);
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
          <CheckCircleOutlineIcon color="success" sx={{ fontSize: 60 }} />
          <Title>
            프로젝트 : {data.projectName} 빌드 #{data.buildNumber} (2022. 4. 11
            오후 10:02:54)
          </Title>
        </Grid>
        <Grid item display="flex" sx={{ m: 4 }}>
          <PersonIcon sx={{ fontSize: 35 }} />
          <User>사용자 {data.gitInfo.username}님이 시작하셨습니다.</User>
        </Grid>
        <Grid item display="flex" sx={{ my: 2, mx: 4 }}>
          <AiOutlineGitlab size={35} />
          <div>
            <GitUrl>
              &nbsp; 깃랩 저장소 :
              <a href="##"> {data.gitInfo.gitRepositoryUrl}</a>
            </GitUrl>
            <Gitbranch>깃랩 브랜치 : {data.gitInfo.gitBranch}</Gitbranch>
          </div>
        </Grid>
        <Box>
          <Typography variant="h4" sx={{ marginY: 3 }}>
            콘솔 로그
          </Typography>
          <Span>{data.consoleLog}</Span>
        </Box>
      </Grid>
    </Box>
  );
}

const Title = styled.div`
  font-size: 35px;
  margin-left: 30px;
`;

const User = styled.div`
  font-size: 25px;
  margin-left: 5px;
`;

const GitUrl = styled.div`
  font-size: 18px;
`;

const Gitbranch = styled.div`
  font-size: 18px;
  margin-left: 10px;
`;

const Log = styled.div`
  display: block;
`;

const Console = styled.h2`
  margin-bottom: 0;
`;

const Span = styled.div`
  width: max;
  padding-left: 5px;
  white-space: pre-wrap;
  background-color: #84898c;
  border: 5px #020202;
  border-radius: 10px;
`;
