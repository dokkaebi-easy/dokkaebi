/* eslint-disable no-nested-ternary */
import React, { useState } from 'react';
import { useStore } from 'Components/Store/SettingStore/SettingStore';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import axios from 'axios';
import Typography from '@mui/material/Typography';

interface PropertyAxios {
  first: string;
  second: string;
}

interface buildConfigAxios {
  frameworkName: string;
  name: string;
  version: string;
  type: string;
  projectDirectory: string;
  buildPath: string;
  env: PropertyAxios[];
  publish: PropertyAxios[];
  volume: PropertyAxios[];
}

export default function AxiosPage() {
  const projectName = useStore((state) => state.projectName);
  const buildConfigs = useStore((state) => state.buildConfigs);
  const gitConfig = useStore((state) => state.gitConfig);
  const nginxConfig = useStore((state) => state.nginxConfig);

  const [nextPage, setNextPage] = useState('');

  const handleClick = () => {
    const data = {
      buildConfigs,
      gitConfig,
      nginxConfig,
      projectName,
    };
    console.log(JSON.stringify(data));

    axios
      .post('/api/project', data)
      .then((res) => {
        console.log(res.data);
        setNextPage('success');
      })
      .catch((error) => {
        console.log(error);
        setNextPage('error');
      });
  };
  return (
    <Box sx={{ display: 'flex', justifyContent: 'center' }}>
      <Box>
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'center',
          }}
          my={3}
        >
          <Typography variant="h4">Build Setting Save</Typography>
        </Box>
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'center',
          }}
        >
          <Typography
            my={5}
            variant="h5"
            align="center"
            color={nextPage === 'error' ? 'red' : 'green'}
          >
            {nextPage
              ? nextPage === 'error'
                ? '저장이 실패하였습니다. 환경설정을 다시 설정 해주세요 '
                : '성공'
              : ''}
          </Typography>
        </Box>
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'center',
          }}
        >
          <Button
            variant="outlined"
            size="large"
            onClick={handleClick}
            sx={{ color: 'black', borderColor: 'black' }}
          >
            Save
          </Button>
        </Box>
      </Box>
    </Box>
  );
}
