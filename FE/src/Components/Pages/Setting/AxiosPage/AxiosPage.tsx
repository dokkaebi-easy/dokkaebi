/* eslint-disable no-nested-ternary */
import React, { useEffect, useState } from 'react';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import axios from 'axios';
import Typography from '@mui/material/Typography';
import { useHistory } from 'react-router';

export default function AxiosPage() {
  const projectId = useSettingStore((state) => state.projectId);
  const projectName = useSettingStore((state) => state.projectName);
  const buildConfigs = useSettingStore((state) => state.buildConfigs);
  const gitConfig = useSettingStore((state) => state.gitConfig);
  const nginxConfig = useSettingStore((state) => state.nginxConfig);
  const dbConfigs = useSettingStore((state) => state.dbConfigs);

  const [nextPage, setNextPage] = useState('');
  const history = useHistory();

  const handleClick = () => {
    const data = {
      buildConfigs,
      dbConfigs,
      gitConfig,
      nginxConfig,
      projectName,
      projectId,
    };
    axios
      .post('/api/project', data)
      .then(() => {
        setNextPage('success');
      })
      .catch((error) => {
        console.log(error);
        setNextPage('error');
      });
  };

  useEffect(() => {
    if (nextPage === 'success') {
      setTimeout(() => {
        history.push('/');
      }, 1000);
    }
  }, [nextPage]);

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
