import React from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import GitLabConnect from 'Components/Pages/Setting/GitLabPage/GitLabConnect/GitLabConnect';
import GitLabRepositories from 'Components/Pages/Setting/GitLabPage/GitLabRepositories/GitLabRepositories';
import { useStore } from 'Components/Store/settingStore';

export default function GitLabPage() {
  const gitConfig = useStore((state) => state.gitConfig);
  // const setGitConfig = useStore((state) => state.setGitConfig);

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper sx={{ padding: 1, textAlign: 'center', width: 200 }}>
          <Typography variant="h5">GitLab Setting</Typography>
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <GitLabConnect gitData={gitConfig} />
          <GitLabRepositories gitData={gitConfig} />
        </Paper>
      </Box>
    </Box>
  );
}
