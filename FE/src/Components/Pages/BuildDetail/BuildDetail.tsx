import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import CancelIcon from '@mui/icons-material/Cancel';
import AutorenewIcon from '@mui/icons-material/Autorenew';
import PersonIcon from '@mui/icons-material/Person';
import { AiOutlineGitlab } from 'react-icons/ai';
import styled from '@emotion/styled';
import Paper from '@mui/material/Paper';
import { Typography } from '@mui/material';
import { useLocation, useParams } from 'react-router-dom';
import axios from 'axios';

export default function BuildDetail() {
  const [detailData, setDetailData] = useState<any>('');
  const location = useLocation();
  const params = useParams();

  useEffect(() => {
    axios.post(`/api/project/build/detail`, params).then((res) => {
      setDetailData(res.data);
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
          {location.state === 'Done' ? (
            <CheckCircleOutlineIcon color="success" sx={{ fontSize: 60 }} />
          ) : (
            <CancelIcon color="error" sx={{ fontSize: 60 }} />
          )}
          <Title>프로젝트 : {detailData.projectName}</Title>
        </Grid>
        <Grid item display="flex">
          <Buildnum>빌드 #{detailData.buildNumber}</Buildnum>
        </Grid>
        <Grid item display="flex" sx={{ m: 4 }}>
          <PersonIcon sx={{ fontSize: 35 }} />
          <User>
            {detailData.gitInfo
              ? `사용자 ${detailData.gitInfo.username}님이 시작하셨습니다.`
              : ' 사용자 없음 '}
          </User>
        </Grid>
        <Grid item display="flex" sx={{ my: 2, mx: 4 }}>
          <AiOutlineGitlab size={35} />
          <div>
            <GitUrl>
              &nbsp; 깃랩 저장소 :
              <a href="##">
                {detailData.gitInfo
                  ? `${detailData.gitInfo.gitRepositoryUrl}`
                  : ' null '}
              </a>
            </GitUrl>
            <Gitbranch>
              깃랩 브랜치 :
              {detailData.gitInfo
                ? `${detailData.gitInfo.gitBranch}`
                : ' null '}
            </Gitbranch>
          </div>
        </Grid>
        <Box>
          <Typography variant="h4" sx={{ marginY: 3 }}>
            콘솔 로그
          </Typography>
          <Span>{detailData.consoleLog}</Span>
        </Box>
      </Grid>
    </Box>
  );
}

const Title = styled.div`
  font-size: 35px;
  margin-left: 30px;
`;

const Buildnum = styled.div`
  font-size: 35px;
  margin-left: 89px;
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
  margin-left: 13px;
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
