import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import GifBoxIcon from '@mui/icons-material/GifBox';
import KeyboardTabIcon from '@mui/icons-material/KeyboardTab';
import PersonIcon from '@mui/icons-material/Person';
import { AiOutlineGitlab } from 'react-icons/ai';
import styled from '@emotion/styled';
import { api } from '../../../api/index';

export default function BuildDetail() {
  // useEffect(() => {
  //   api.get(`project/build/detail/buildStateId?buildStateId=${}`).then((res) => {
  //     console.log(res);
  //   });
  // }, []);

  return (
    <Box
      mt={3}
      sx={{ display: 'flex', alignItems: 'center', justifyContent: 'left' }}
    >
      <Grid container direction="column">
        <Grid item display="flex">
          <CheckCircleOutlineIcon color="success" sx={{ fontSize: 60 }} />
          <Title> Build #294 (2022. 4. 11 오후 10:02:54) </Title>
        </Grid>
        <Grid item display="flex" sx={{ m: 4 }}>
          <PersonIcon sx={{ fontSize: 35 }} />
          <User> 사용자 홍길동님이 시작하셨습니다.</User>
        </Grid>
        <Grid item display="flex" sx={{ my: 2, mx: 4 }}>
          <AiOutlineGitlab size={35} />
          <div>
            <GitInfo> &nbsp; Revision: ㅁ어낢ㄴ아ㅣ렁나ㅣ런ㄻㅁㄹ</GitInfo>
            <GitUrl>
              &nbsp; &nbsp; 깃랩 저장소:
              <a href="##">https://sadsadsadasdsadsadsadsadsad</a>
            </GitUrl>
          </div>
        </Grid>
        <Log>
          <KeyboardTabIcon sx={{ fontSize: 45 }} />
          <br />
          <Span>
            sdaaaaaaaaaaafasfdsfsdafdsafsdafsdafasdfsd
            aaaaaaaaaaafasfdsfsdafdsafsdafsdafasdfsdaa
            aaaaaaaaafasfdsfsdafdsafsdafsdafasdfsdaaaa
            aaaaaaafasfdsfsdafdsafsdafsdafasdfsdaaaaaa
            aaaaafasfdsfsdafdsafsdafsdafasdfsdaaaaaaaa
            aaafasfdsfsdafdsafsdafsdafasdfsdaaaaaaaaaa
            afasfdsfsdafdsafsdafsdafasdfsdaaaaaaaaaaaf
            asfdsfsdafdsafsdafsdafasdfsdaaaaaaaaaaafas
            fdsfsdafdsafsdafsdafasdfsdaaaaaaaaaaafasfds
            fsdafdsafsdafsdafasdfsdaaaaaaaaaaafasfdsfsd
            afdsafsdafsdafasdfsdaaaaaaaaaaafasfdsfsdafd
            safsdafsdafasdfsdaaaaaaaaaaafasfdsfsdafdsafsdafsdafasdfsdaaaaaaaaaaafasfdsfsdafdsafsdafsdafasdfsdaaaaaaaaaaafasfdsfsdafdsafsdafsdafasdfsdaaaaaaaaaaafasfdsfsdafdsafsdafsdafasdfsdaaaaaaaaaaafasfdsfsdafdsafsdafsdafasdfsdaaaaaaaaaaafasfdsfsdafdsafsdafsdafasdf
          </Span>
        </Log>
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
  margin-left: 30px;
`;

const GitInfo = styled.div`
  display: flex;
  font-size: 25px;
`;

const GitUrl = styled.div`
  font-size: 18px;
`;

const Log = styled.div`
  display: block;
`;

const Span = styled.span`
  width: 800px;
  white-space: normal;
  word-break: break-all;
`;
