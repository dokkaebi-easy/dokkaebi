import React from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import GitLabConnect from 'Components/Pages/Setting/GitPage/Connect/Connect';
import GitLabRepositories from 'Components/Pages/Setting/GitPage/Repositories/Repositories';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import ConnectCheck from './ConnectCheck/ConnectCheck';

export default function GitLabPage() {
  const gitConfig = useSettingStore((state) => state.gitConfig);

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper
          sx={{
            padding: 1,
            textAlign: 'center',
            width: 200,
            color: ' white',
            background: 'linear-gradient(195deg, #666, #191919)',
          }}
        >
          <Typography variant="h5">Git Setting</Typography>
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <GitLabConnect gitData={gitConfig} />
          <GitLabRepositories gitData={gitConfig} />
          <ConnectCheck gitData={gitConfig} />
        </Paper>
      </Box>
    </Box>
  );
}
