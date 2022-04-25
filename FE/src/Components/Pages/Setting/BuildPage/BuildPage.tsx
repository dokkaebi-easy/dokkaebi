import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';

import Typography from '@mui/material/Typography';
import SelectItem from 'Components/UI/Molecules/SelectItem/SelectItem';

const FramAndLib = ['SpringBoot', 'Django', 'Vue', 'React', 'MySQL'];

// const Version = {
//   SpringBoot: [],
//   Django: [],
//   Vue: [],
//   React: [],
//   MySQL: [],
// };

export default function BuildPage() {
  const [select, setSelect] = useState('');

  const handleChangeFrame = (event: string) => {
    setSelect(event);
  };

  return (
    <Box>
      <Typography sx={{ mt: 2, mb: 1 }} variant="h3">
        Build Setting
      </Typography>
      <Grid container spacing={2}>
        <Grid item>
          <Typography>프레임워크/ 라이브러리</Typography>
          <SelectItem Items={FramAndLib} change={handleChangeFrame} />
        </Grid>
        <Grid item>
          <Typography>버전</Typography>
          {/* <SelectItem Items={Version[]} /> */}
        </Grid>
        <Grid item>
          <Typography>...</Typography>
          {/* <SelectItem /> */}
        </Grid>
      </Grid>
    </Box>
  );
}
