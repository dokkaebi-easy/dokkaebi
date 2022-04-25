import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import GitLabConnect from 'Components/Pages/Setting/GitLabPage/GitLabConnect/GitLabConnect';
import GitLabRepositories from 'Components/Pages/Setting/GitLabPage/GitLabRepositories/GitLabRepositories';
import GitData, { Git } from 'Components/MDClass/GitData/GitData';

export default function GitLabPage() {
  const [gitData, setGitData] = useState<Git>(new GitData());

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper sx={{ padding: 1, textAlign: 'center', width: 200 }}>
          <Typography variant="h5">GitLab Setting</Typography>
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <GitLabConnect gitData={gitData} />
          <GitLabRepositories gitData={gitData} />
        </Paper>
      </Box>
    </Box>
  );
}
