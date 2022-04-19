import React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import MDTable from 'Components/UI/Molecules/MDTable/MDTable';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import CreateIcon from '@mui/icons-material/Create';

export default function Home() {
  return (
    <Box sx={{ marginTop: 5 }}>
      <MDTable />
      <Stack mt={2} direction="row" justifyContent="flex-end" spacing={2}>
        <Button variant="outlined" startIcon={<DeleteIcon />}>
          Delete
        </Button>

        <Button variant="contained" endIcon={<CreateIcon />}>
          Create
        </Button>
      </Stack>
    </Box>
  );
}
